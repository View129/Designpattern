package com.wyy.responsibility;

public class Main {

    public static void main(String[] args) {
       String msg = "敏感,符号,大家好！";
       Requerst requerst = new Requerst();
       requerst.setRequerstStr(msg);
       Respones respones = new Respones();
       respones.setResponesStr("respones");
       FilterChain fc = new FilterChain();
       fc.addFilter(new HtmlFilter()).addFilter(new SesitiveFilter());

       fc.doFilter(requerst,respones,fc);

        System.out.println(requerst.requerstStr);
        System.out.println(respones.responesStr);

    }
}
