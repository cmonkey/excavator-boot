## Authorization

用户通过一下代码进行对token 的生成

    @Resource
    private TokenManager tokenManager;
    
    @PostMapping("/users")
    public ResponseEntity<UserInfo> login(@RequestBody User user){
      // check 
      
      // get token 
      String token = tokenManager.createToken(customerId).getToken();
      
      UserInfo userInfo = new UserInfo(token);
      
      return new ResponseEntity.ok(userInfo);
    }
    
当前实现的token存储在redis 中，默认过期时间为15天

由于在默认权限拦截中，当权限验证通过，需要设置滑动过期时间

没有将过期时间修改为用户输入, 所以需要注意过期时间问题

### 在业务程序中得到当前用户
需要在业务程序中编写一下类似代码，用于对token 获取customer 对象

    @Component
    public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
        private final static Logger logger = LoggerFactory.getLogger(CurrentUserMethodArgumentResolver.class);
        @Resource
        private CustomerService customerService;

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(Customer.class)
                    && parameter.hasParameterAnnotation(CurrentUser.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            Long customerId = (Long)webRequest.getAttribute(TokenConstants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);

            logger.info("resolveArgument param customerId = {}", customerId);

            if(null != customerId){
                return customerService.findCustomerById(customerId);
            }

            throw new MissingServletRequestPartException(TokenConstants.CURRENT_USER_ID);
        }
    }
    
并在项目配置中添加
authorization.method.resolvers=currentUserMethodArgumentResolve

### 手动指定api 鉴权并得到当前用户信息

     @GetMapping("/users/info")
     @Authorization //返回用户信息
     public ResponseEntity<User> info(@CurrentUser Customer customer){
       // TODO 
     }
