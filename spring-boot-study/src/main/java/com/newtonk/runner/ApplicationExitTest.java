package com.newtonk.runner;

import org.springframework.boot.ExitCodeGenerator;

/**
 * Created by newtonk on 2017/5/26.
 */
public class ApplicationExitTest implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 0;
    }
}
