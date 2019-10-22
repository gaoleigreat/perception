package com.lego.framework.base.interceptor;

import com.lego.framework.base.context.RequestContext;
import com.lego.framework.core.annotation.Operation;
import com.lego.framework.core.annotation.Resource;
import com.lego.framework.core.consts.RespConsts;
import com.lego.framework.core.vo.CurrentVo;
import com.lego.framework.core.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class SecurityInterceptor implements MethodInterceptor {

    @Value("${spring.application.name}")
    private String scope;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> c = invocation.getMethod().getDeclaringClass();
        Resource resource = c.getAnnotation(Resource.class);
        Operation operation = invocation.getMethod().getAnnotation(Operation.class);

        if (null != resource && null != operation) {
            CurrentVo currentVo = RequestContext.getCurrent();
            if(null != currentVo &&  !currentVo.isPermissionChecked()){
                String permission = scope + "$" + resource.value() + "$" + operation.value();
                if(null == currentVo.getPermissions() || !currentVo.getPermissions().contains(permission)){
                    Class returnType = invocation.getMethod().getReturnType();
                    if("java.util.Map".equals(returnType.getName())){
                        return RespVOBuilder.failure(RespConsts.FAIL_NOPRESSION_CODE,RespConsts.FAIL_NOPRESSION_MSG);
                    }else if("com.survey.lib.common.vo.RespVO".equals(returnType.getName())){
                        return RespVOBuilder.failure(RespConsts.FAIL_NOPRESSION_CODE,RespConsts.FAIL_NOPRESSION_MSG);
                    }else if("java.util.List".equals(returnType.getName())){
                        return Collections.EMPTY_LIST;
                    }
                    return null;
                }
                currentVo.setPermissionChecked(true);
            }
        }

        return invocation.proceed();
    }

}
