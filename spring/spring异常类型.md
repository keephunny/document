* BindException 400 - Bad Request
* ConversionNotSupportedException 500 - Internal Server Error
* HttpMediaTypeNotAcceptableException 406 - Not Acceptable
* HttpMediaTypeNotSupportedException 415 - Unsupported Media Type
* HttpMessageNotReadableException 400 - Bad Request
* HttpMessageNotWritableException 500 - Internal Server Error
* HttpRequestMethodNotSupportedException 405 - Method Not Allowed
* MethodArgumentNotValidException 400 - Bad Request
* MissingServletRequestParameterException 400 - Bad Request
* MissingServletRequesPartException 400 - Bad Request
* NoSuchRequestHandlingMethodException 404 - Not Found
* TypeMismatchException 400 - Bad Request


@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e){
       
        //请求方式不正确
        if(e instanceof HttpRequestMethodNotSupportedException){
            return ResultUtil.error(ExceptionEnum.HTTP_METHOD);
        }
        //404
        if(e instanceof NoHandlerFoundException){//404
            return ResultUtil.error(ExceptionEnum.NO_METHOD_ERROR);
        }
        /**
         * 如果抛出的自定义redis 异常
         */
        if(e instanceof RedisException){
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(myException.getCode(),myException.getMessage());
        }
        /**
         * 自定义错误异常
         */
        if(e instanceof CustomException){
            return ResultUtil.error(((CustomException)e).getCode(),((CustomException)e).getMessage());
        }
        /**
         * 权限验证
         */
        if(e instanceof AuthenticationException){
            return ResultUtil.error(ExceptionEnum.SHIRO_LOGIN_ERROR);
        }
        if(e instanceof UnauthorizedException ){
            return ResultUtil.error(ExceptionEnum.NO_AUTHORITY);
        }

 
        if(e instanceof TokenException){
            return ResultUtil.error(ExceptionEnum.TOKENT_ERROR);
        }
        LOGGER.error("未知异常",e);
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }