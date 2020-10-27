package com.myz.starters.distribute.sample.web.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/24 11:00
 * @email 2641007740@qq.com
 */
public class BodyContentMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(BodyContent.class) !=null && parameter.getParameterType().isAssignableFrom(Map.class);
    }

    /**
     * {@link org.springframework.web.filter.FormContentFilter} 参考解析post请求的body
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

//        ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
//        HttpServletRequest request = servletWebRequest.getRequest();
//        if (HttpMethod.GET.name().equals(servletWebRequest.getHttpMethod().name())){ // options请求不会走到这里
//            String bodySecret = webRequest.getParameter("s");
//            if (bodySecret == null || bodySecret.length() == 0) {
//                throw new BodyContentResolverException("方法参数有@BodyContent注解，请求方法为GET参数中无s");
//            }
//            return JSONObject.parseObject(EncryptUtil.decrypt(bodySecret));
//        }
//        MediaType mediaType = MediaType.parseMediaType(request.getContentType());
//        if (!MediaType.APPLICATION_JSON.includes(mediaType)) {
//            throw new BodyContentResolverException("方法参数有@BodyContent注解，仅支持Content-type:application/json;");
//        }
//
//        HttpInputMessage inputMessage = new ServletServerHttpRequest(request) {
//            @Override
//            public InputStream getBody() throws IOException {
//                return request.getInputStream();
//            }
//        };
//
//        MediaType contentType = inputMessage.getHeaders().getContentType();
//        Charset charset = (contentType != null && contentType.getCharset() != null ?
//                contentType.getCharset() : Charset.forName("UTF-8"));
//        String body = StreamUtils.copyToString(inputMessage.getBody(), charset);
//        if(body == null || body.length() == 0){
//            return null;
//        }
//        JSONObject bodyObject = JSONObject.parseObject(body);
//        String bodySecret = bodyObject.getString("s");
//        if (bodySecret == null || bodySecret.length() == 0) {
//            throw new BodyContentResolverException("方法参数有@BodyContent注解，请求方法为POST Body体中无s");
//        }
//        return JSONObject.parseObject(EncryptUtil.decrypt(bodySecret));
////        JSONObject sContent = JSONObject.parseObject(EncryptUtil.decrypt(bodySecret));
////        bodyObject.putAll(sContent);
//////        bodyObject.remove("s");// bodyObject依然含有s，可以用作兼容（其实没有必要兼容）
////        return bodyObject;
        return null;
    }

}
