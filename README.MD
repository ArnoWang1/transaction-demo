### 分布式事务普通示例，运行步骤
##### 1、执行sql目录脚本，示例用了2个库，数据库使用的是mysql
##### 2、启动2个服务，默认端口分别是10010和10020
##### 3、浏览器输入测试用例URL
```html
分服务Try失败用例
http://127.0.0.1:10010/user/business?username=admin&orderNo=2019
```
```html
主服务失败用例
http://127.0.0.1:10010/user/business?username=no&orderNo=2020
```
```html
事务正常完成用例
http://127.0.0.1:10010/user/business?username=admin&orderNo=2020
```