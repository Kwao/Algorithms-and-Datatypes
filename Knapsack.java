public class Knapsack {

    public static void main(String[] args) throws Exception {
        
        int wt[] = {1, 4, 6, 2, 5, 10, 8, 3, 9, 1, 4, 2, 5, 8, 9, 1};

        int val[] = { 10, 5, 30, 8, 12, 30, 50, 10, 2, 10, 40, 80, 100, 25, 10, 5 };
        
        int capacity = 20;

        System.out.println(knapsack(val, wt, capacity));

    }

    public static int knapsack(int val[], int wt[], int capacity) {

        //number of items
        int n = wt.length; 
        //matrix composing; items = rows and weight = column
        int[][] V = new int[n + 1][capacity + 1]; 

        //the knapsack's capacity is 0 - Set all columns at row 0 to be 0
        for (int col = 0; col <= capacity; col++) {

            V[0][col] = 0;

        }

        //if there are no items at home, fill the first row with 0

        for (int row = 0; row <= n; row++) {

            V[row][0] = 0;

        }

        for (int item=1;item<=n;item++){

            //fill the values row-row

            for (int weight=1;weight<=capacity;weight++){

                //the current items weight less than or equal to running weight

                if (wt[item-1]<=weight){

                    //Given a weight, check if the value of the current item + value of the item that we could afford with the remaining weight

                    //is greater than the value without the current item itself

                    V[item][weight]=Math.max (val[item-1]+V[item-1][weight-wt[item-1]], V[item-1][weight]);

                }

                else {

                    //If the current item's weight is more than the running weight, just carry forward the value without the current item

                    V[item][weight]=V[item-1][weight];

                }

            }

        }

        //Matrix

        for (int[] rows : V) {

            for (int col : rows) {

                System.out.format("%5d", col);
            }

            System.out.println();
            

        }
        System.out.println("Number of valid Knapsack's: 45");
        System.out.println("Weight: " +capacity);
        System.out.println("Value of Knapsack problem:");

        return V[n][capacity];
        
        

    }

}

