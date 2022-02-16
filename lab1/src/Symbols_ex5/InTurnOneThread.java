package Symbols_ex5;

public class InTurnOneThread implements Runnable{
    Symbol symbol;

    public InTurnOneThread(Symbol symbol){
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            symbol.setAndPrintOne();
            if (i!=0 && i%50==0) System.out.println();
        }
    }
}
