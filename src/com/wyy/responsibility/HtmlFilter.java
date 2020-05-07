package com.wyy.responsibility;

public class HtmlFilter implements Filter {
    @Override
    public void doFilter(Requerst requerst, Respones respones, FilterChain chain) {
        requerst.requerstStr = requerst.requerstStr.replace("敏感", "**");
        requerst.requerstStr += "-HtmlFiltre";
        chain.doFilter(requerst, respones, chain);
        respones.responesStr += "-HtmlFiltre";
    }
}
