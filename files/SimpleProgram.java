/*
 * Class declaration
 */
class Test extends Base {
    int x;
    boolean check;

    public int ComputeFac(int num, int[] redundant){
        int num_aux;
        boolean another_redundant;

        Test another_one;
        Base and_another_one;
        //num_aux = x + 5 * 4 / (2 + 3 / 4);

        //x = new Base().func((x - 1 + 3) * 4 + 3 / this.gimmeFive(), !check && false + new int[5].length);
        num_aux = x + 5;
        x = x + 3;

        redundant[5].f();

        Test.gimmeFive(x, redundant[this.gimmeFive()]);

        //num_aux = x + 5 * 4 / (2 + 3 / 4);
        if (check && another_redundant)
            num_aux = Test.gimmeFive();
        else {
            //num_aux = x + 5 * 4 / (2 + 3 / 4);
            while (num < 1) {
               x = x + 5;
               //num_aux = x + 5 * 4 / (2 + 3 / 4);            
            }

            if (num < 1)
                num_aux = redundant[5].gimmeFive();//num_aux = x + 5 * 4 / (2 + 3 / 4);
            else
                num_aux = x + 5 * 4 / (2 + 3 / 4);
            
            x = new Base().func((x + 3) * 4 + 3 / this.gimmeFive(), !check && false + new int[5].length);
        }
        
        //num_aux = x + 5 * 4 / (2 + 3 / 4);
        num_aux = x + 2 * 6;

        return x + 5 * 4 / (2 + redundant[3] / 4);
    }

    public int gimmeFive() {return 5;}
}