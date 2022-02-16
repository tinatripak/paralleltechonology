package Symbols_ex5;

public class SymbolTwoThread implements Runnable{
    private final String symbol;

    public SymbolTwoThread(String s){
        this.symbol = s;
    }

    @Override
    public void run(){
        for(int i=1; i<50; i++){
            System.out.println(symbol);
        }
    }

}
