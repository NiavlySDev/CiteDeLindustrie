package fr.niavlys.dev.ci.economy;

import fr.niavlys.dev.bn.main.BigNumbers;

public class Balance {

    private BigNumbers bronze;
    private BigNumbers argent;
    private BigNumbers or;

    public Balance(){
        this.bronze = new BigNumbers(0);
        this.argent = new BigNumbers(0);
        this.or = new BigNumbers(0);
    }

    public BigNumbers getBronze() {
        return bronze;
    }

    public BigNumbers getArgent() {
        return argent;
    }

    public BigNumbers getOr() {
        return or;
    }
}
