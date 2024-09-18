#README
A basic servlet web application to get started and to use as a reference. Uses an H2 database saved to the application WEB-INF directory. Configured for Basic authentication using the database.

#Setup Process
Update path references to the Tomcat instance that you are using.

For the authentication setup from the database, the following items must be added to the Tomcat's server.xml.

<Resource name="jdbc/dev_db" auth="Container"
type="javax.sql.DataSource"
driverClassName="org.h2.Driver"
username="sa"
password="sa"
url="jdbc:h2:../webapps/webapp/WEB-INF/dev_db"/>

<Realm className="org.apache.catalina.realm.DataSourceRealm"
dataSourceName="jdbc/dev_db"
userTable="users" userNameCol="username" userCredCol="password"
userRoleTable="user_roles" roleNameCol="role_name"/>