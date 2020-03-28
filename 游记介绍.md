# dailyTravel
原型ok，项目进展到新增分类<br>
[SpringBoot开发一个小而美的个人博客](https://www.bilibili.com/video/av72035869)<br>
我在代码中写了大量注释，或许可以参考<br>
## 视频中用到的网站
[semantic](https://semantic-ui.com/)<br>
[平滑滚动](https://github.com/flesler/jquery.scrollTo)<br>
[文章二维码生成](http://davidshimjs.github.io/qrcodejs/)<br>
[目录生成](https://tscanlin.github.io/tocbot/)<br>
[代码高亮](https://github.com/PrismJS/prism)<br>
[网页排版](https://github.com/sofish/typo.css)<br>
[占位图片](https://picsum.photos/)<br>
[CDN加速](https://www.bootcdn.cn/)  [CDN加速](https://www.jsdelivr.com/)<br>
[背景图片](https://www.toptal.com/designers/subtlepatterns/)<br>
[markdown编辑器](https://pandao.github.io/editor.md/)<br>
还发现了个调色网站，还没想到用处，不过感觉很好看<br>
[栗紫](http://zhongguose.com/#lizi)<br>
## 问题
+ Required String parameter 'username' is not present
+ 看看需要写username的地方，是否写的username。比如我，是login表单处写错了
+ WebMvcConfigurerAdapter [方法过时](https://blog.csdn.net/qq_38164123/article/details/80392904)
+ [Cannot resolve table 't_daily'](https://blog.csdn.net/moqianmoqian/article/details/105014348)
+ [Server returns invalid timezone.]
(https://blog.csdn.net/wsf0001/article/details/103391810)
+ [idea之springboot端口被占用/跳转到login](https://blog.csdn.net/moqianmoqian/article/details/104903180)
+ Inferred type 'S' for type parameter 'S' is [not within its bound;](https://blog.csdn.net/moxiaoya1314/article/details/80037290)
## 注解
+ @Service：标注业务层组件
+ @Controller：标注控制层组件
+ @Autowired：自动装配
+ @ResponseStatus(HttpStatus.NOT_FOUND) ：把此Exception作为资源找不到的状态
+ @RequestMapping()：映射请求
+ @Configuration：配置类
+ @Transactional：事务，保持数据的一致性
+ @Entity(name="t_type")：表明该类为实体类，且对应表为t_type
+ @Valid Type type：校验type对象
+ @PostMapping("/types") ：post和get同名不会冲突
+ 表示id为主键，策略为自增长
```java
@Id
    @GeneratedValue
    private Long id;
```
+ @PathVariable接收路径中 id
 ```java
   @GetMapping("types/{id}/input") 
    public String editInput(@PathVariable Long id)
```

