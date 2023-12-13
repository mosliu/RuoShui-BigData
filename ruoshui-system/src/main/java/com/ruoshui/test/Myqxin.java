package com.ruoshui.test;

import ch.ethz.ssh2.Connection;
import com.ruoshui.flink.utils.JavaExecLinuxCommandRemote;
import com.ruoshui.flink.utils.RemoteCommandUtil;

import java.io.File;

public class Myqxin {
    public static void main(String[] args)throws Exception {
        String command = "cat  >"+ "/lsx/3.text" +"<< 'EOF' \n" + "4rfv$REY" + "\n" + "EOF";;
        JavaExecLinuxCommandRemote s = new JavaExecLinuxCommandRemote();
        s.RemoteSubmitCommand(command,"192.168.172.173","root","mendale$5790");
    }
}

