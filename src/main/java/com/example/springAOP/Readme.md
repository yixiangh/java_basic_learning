**1.概念：**
    1.1.Advice:处理，包括处理时机和处理内容，处理内容就是要做什么事，比如校验权限和记录日志。处理时机就是在什么时机执行处理内容，分为前置处理（即业务代码执行前）、后置处理（业务代码执行后）等。‘
        主要类型有：
        1.1.1.@Before：该注解标注的方法在业务模块代码执行之前执行，其不能阻止业务模块的执行，除非抛出异常；
        1.1.2.@AfterReturning：该注解标注的方法在业务模块代码执行之后执行；
        1.1.3.@AfterThrowing：该注解标注的方法在业务模块抛出指定异常后执行；
        1.1.4.@After：该注解标注的方法在所有的Advice执行完成后执行，无论业务模块是否抛出异常，类似于finally的作用；
        1.1.5.@Around：该注解功能最为强大，其所标注的方法用于编写包裹业务模块执行的代码，其可以传入一个ProceedingJoinPoint用于调用业务模块的代码，无论是调用前逻辑还是调用后逻辑，都可以在该方法中编写，甚至其可以根据一定的条件而阻断业务模块的调用；
        1.1.6.@DeclareParents：其是一种Introduction类型的模型，在属性声明上使用，主要用于为指定的业务模块添加新的接口和相应的实现。
        这里需要说明的是，@Before是业务逻辑执行前执行，与其对应的是@AfterReturning，而不是@After，@After是所有的切面逻辑执行完之后才会执行，无论是否抛出异常。
    1.2.Pointcut:切点，决定在什么地方切入业务代码
          1.2.1.切点分为execution方式和annotation方式。前者可以用路径表达式指定哪些类织入切面，后者可以指定被哪些注解修饰的代码织入切面
    1.3.Aspect:切面，即Pointcut和Advice
    1.4.joint point:连接点，是程序执行的一个点。例如，一个方法的执行或者一个异常的处理。在SpringAOP中,一个连接点总代表一个方法的执行
    1.5.Weaving:织入，就是通过动态代理，在目标对象方法中执行处理内容的过程
    1.6.JointPoint 对象很有用，可以用它来获取一个签名，利用签名可以获取请求的包名、方法名，包括参数（通过 joinPoint.getArgs() 获取）等。
**2.注解：**
    2.1.@Aspect：作用在类上，表明一个切面
    2.2.@Pointcut：用来定义一个切面
    2.3.@Around：既可以在目标方法之前织入增强动作，也可以在执行目标方法之后织入增强动作；
                它可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标目标方法的执行；
                它可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值；
                当需要改变目标方法的返回值时，只能使用Around方法；
                虽然Around功能强大，但通常需要在线程安全的环境下使用。因此，如果使用普通的Before、AfterReturing增强方法就可以解决的事情，就没有必要使用Around增强处理了。
    2.4.@Before：注解指定的方法在切面切入目标方法之前执行
    2.5.@After：指定的方法在切面切入目标方法之后执行
    2.6.@AfterReturning：@AfterReturning 注解和 @After 有些类似，区别在于 @AfterReturning 注解可以用来捕获切入方法执行完之后的返回值，对返回值进行业务逻辑上的增强处理
        /**
         * 在上面定义的切面方法返回后执行该方法，可以捕获返回对象或者对返回对象进行增强
         * @param joinPoint joinPoint
         * @param result result
         */
        @AfterReturning(pointcut = "pointCut()", returning = "result")
        public void doAfterReturning(JoinPoint joinPoint, Object result) {
            Signature signature = joinPoint.getSignature();
            String classMethod = signature.getName();
            log.info("方法{}执行完毕，返回参数为：{}", classMethod, result);
            // 实际项目中可以根据业务做具体的返回值增强
            log.info("对返回参数进行业务上的增强：{}", result + "增强版");
        }
    2.6.@AfterThrowing：当被切方法执行过程中抛出异常时，会进入 @AfterThrowing 注解的方法中执行，在该方法中可以做一些异常的处理逻辑。要注意的是 throwing 属性的值必须要和参数一致，否则会报错。该方法中的第二个入参即为抛出的异常。
     /**
         * 在上面定义的切面方法执行抛异常时，执行该方法
         * @param joinPoint jointPoint
         * @param ex ex
         */
        @AfterThrowing(pointcut = "pointCut()", throwing = "ex")
        public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
            Signature signature = joinPoint.getSignature();
            String method = signature.getName();
            // 处理异常的逻辑
            log.info("执行方法{}出错，异常为：{}", method, ex);
        }