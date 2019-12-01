public class Main {

    public static void main(String[] args){
        ACO aco;
        Create_Map create_map;
        aco=new ACO();
        int node_num=50;
        create_map=new Create_Map();
        int Max=1000;
        int max_gen=10;                //迭代次数


        int[][] G2=new int[][]{
                {Max,2,Max,Max,Max},
                {2,Max,5,2,Max},
                {Max,5,Max,4,Max},
                {Max,2,4,Max,5},
                {Max,Max,Max,5,Max}
                };
        create_map.Map_Create(node_num);
        int[][] G;
        G=create_map.getMap();
        create_map.Show_Map();
        aco.init(G,100,max_gen);
        aco.run(max_gen);
        aco.ReportResult();
        create_map.Show_Map();

    }
}
