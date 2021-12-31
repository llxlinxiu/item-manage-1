package com.manage.common.handler;



import com.manage.common.result.R;
import com.manage.common.result.ResultCodeEnum;
import com.manage.common.util.ExceptionUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: DaPang
 * @Date: 2020/03/24/9:34
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 所有controller层抛出的异常都会在此捕获
     * 捕获Exception异常对象和Exception的子类异常对象
     * @param e
     * @return 返回R对象的json形式
     */
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        //打印异常跟踪栈，在控制台中显示异常跟踪信息
//        e.printStackTrace();
        //遵循日志记录器的配置输出日志
        log.error(ExceptionUtils.getMessage(e));
        //返回异常结果的r对象
        return R.error();
    }

    /**
     * 捕获特定异常对象 BadSqlGrammarException
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R error(BadSqlGrammarException e){
        System.out.println("BadSqlGrammarException");
        //打印异常跟踪栈，在控制台中显示异常跟踪信息
//        e.printStackTrace(); //输出到控制台
        //遵循日志记录器的配置输出日志
        log.error(ResultCodeEnum.BAD_SQL_GRAMMAR.toString());
        log.error(ExceptionUtils.getMessage(e));
        //返回异常结果的r对象
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    /**
     * 捕获特定异常对象 HttpMessageNotReadableException
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R error(HttpMessageNotReadableException e){
        System.out.println("HttpMessageNotReadableException");
        //打印异常跟踪栈，在控制台中显示异常跟踪信息
//        e.printStackTrace();
        //遵循日志记录器的配置输出日志
        log.error(ResultCodeEnum.JSON_PARSE_ERROR.toString());
        log.error(ExceptionUtils.getMessage(e));
        //返回异常结果的r对象
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public R error(JwtException e){
        System.out.println("JwtException");
        //打印异常跟踪栈，在控制台中显示异常跟踪信息
//        e.printStackTrace();
        //遵循日志记录器的配置输出日志
        log.error(ResultCodeEnum.JSON_PARSE_ERROR.toString());
        log.error(ExceptionUtils.getMessage(e));
        //返回异常结果的r对象
        return R.error().message(e.getMessage());
    }
    //自定义异常处理
    //使用一个异常处理方法，处理所有的异常信息，并显示个性化的异常结果
}
