
准备工作：
1、建数据库、sql执行、jdbc文件配置
2、执行testInit准备测试用户数据

--------- 看点 --------- 
1、TestJDBCRelation	
	
建好数据库，搭好Mybatis框架并写好userDao,addressDao,测试dao
以纯xml方式使用mybatis，引入了BaseDao



--- 问题
user[1:N]Addr
loadUser
listUser
<collection

Addr[1:1]User
loadAddr
listAddr
<association