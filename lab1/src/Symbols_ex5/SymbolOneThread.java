package Symbols_ex5;

public class SymbolOneThread implements Runnable {
    private final String symbol;

    public SymbolOneThread(String s){
        this.symbol = s;
    }

    @Override
    public void run(){
        for(int i=1; i<50; i++){
            System.out.println(symbol);
        }
    }
}
