package com.newtonk.externalizedConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * ConfigurationProperties可注入属性到bean中，这里以my开头的属性，enable这种会自动按名称注入，
 * 还支持嵌套注入。 注入后需要用EnableConfigurationProperties注册到Configuration中为一个bean
 *
 * 注意一定要写getset方法
 * 当然也可以直接注解到bean上就不需要注册为配置了
 */
//@ConfigurationProperties(prefix = "my")
@Getter@Setter
public class MyProperties {
    private boolean enable;
    private String name;
    private int age;
    private Security security = new Security();

    public static class Security {
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
