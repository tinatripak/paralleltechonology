package Symbols_ex5;

public class InTurnTwoThread implements Runnable{
    Symbol symbol;

    public InTurnTwoThread(Symbol symbol){
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            symbol.setAndPrintTwo();
            if (i!=0 && i%50==0) System.out.println();
        }
    }
}
