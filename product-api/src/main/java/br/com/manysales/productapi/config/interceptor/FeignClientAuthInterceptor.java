package br.com.manysales.productapi.config.interceptor;

import br.com.manysales.productapi.config.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class FeignClientAuthInterceptor implements RequestInterceptor {
    public static String AUTH = "Authorization";
    @Override
    public void apply(RequestTemplate template){
        HttpServletRequest currentRequest = this.getCurrentRequest();
        template.header(AUTH, currentRequest.getHeader(AUTH));
    }

    private HttpServletRequest getCurrentRequest(){
        try{
            return (
                    (ServletRequestAttributes) Objects.
                            requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest();

        }catch (Exception e){
            e.printStackTrace();
            throw new ValidationException("The current request could not be proccessed!");
        }
    }
}
