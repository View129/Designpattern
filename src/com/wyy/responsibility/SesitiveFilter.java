
package com.wyy.responsibility;

public class SesitiveFilter implements Filter {
    @Override
    public void doFilter(Requerst requerst, Respones respones, FilterChain chain) {
        requerst.requerstStr=requerst.requerstStr.replace("符号","**");
        requerst.requerstStr+="-SesitiveFilter";
        chain.doFilter(requerst,respones,chain);
        respones.responesStr+="-SesitiveFilter";
    }
}