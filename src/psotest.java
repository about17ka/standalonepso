import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class psotest {
    
    private final int iRang= 30;	////������ȡֵ��Χ
    static int dim =2;	//�Ż�������ά��
    static int sizepop =20;	//������
    double sum1 = 0;
    double sum2 = 0;
    double g = 10000;	//ȫ�����Ž�
    private Random random =new Random();	//����һ�����ֵ
    public double[][] pop =new double[sizepop][dim];	//���ӵ�λ��
    public double[][] dpbest= new double[sizepop][dim];	//���Ӽ�¼������λ��
    public double[][] V =new double[sizepop][dim];	//���ӵ��ٶ�
    public double[] fitness= new double[sizepop];	//���ӵĵ�ǰ��
    public double[] gbest =new double[dim];	 	//ȫ�����Ž��λ��
    public double[]fitnessgbest = new double[sizepop];	//ȫ�����Ž������
    public double[]bestfitness = new double[sizepop];	//���ӱ�������Ž�
    int m_iTempPos =999;

    public void c() {
       for(int i = 0; i < sizepop;i++)	//����ÿһ�����ӵĳ�ʼ��
       {
           for(int j= 0; j < dim; j++) 
           {
              pop[i][j] = (random.nextDouble() - 0.5) * 2 *iRang; 	//��ʼ�����ӵ�λ��
              V[i][j] = dpbest[i][j] =pop[i][j];	//��ʼ�����ӵ��ٶ�
           }
           for(int j= 0; j < dim; j++) 
           {
              sum1 += Math.pow(pop[i][j],2);
              sum2 += Math.cos(2*Math.PI*pop[i][j]);
           }
           fitness[i]= -20 * Math.exp(-0.2 * Math.sqrt((1.0 / dim) * sum1))
                 - Math.exp((1.0 / dim) * sum2) + 20 +2.72;	//���㵱ǰ���ӵĽ�
          bestfitness[i] = 10000;	//���ӱ�������Ž⸳ֵΪһ�����ֵ��Ŀ��������Сֵ��
          if(bestfitness[i] > fitness[i]) 
          {
              bestfitness[i] =fitness[i];
              for(int j = 0; j< dim; j++) 
              {
                 dpbest[i][j] = pop[i][j];	//��ǰ���ӱ�������Ž�Ϊ���ӵĵ�ǰ�⣬�Ҽ�¼������λ��Ϊ���ӵĵ�ǰλ��
              }
           }
       }
       
       for(int i = 0; i < sizepop;i++)	//��ʼ�����֮�������е�������Ѱ�����Ž⣬
    	   								//��ǰȫ�����Ž�Ϊg����Ӧ���ӵ�λ����gbest[j]��
       {
           if(bestfitness[i] < g) 
           {
                 g = bestfitness[i];
                 m_iTempPos = i;
           }
       }
       if (m_iTempPos != 999) 
       {
           for (int j= 0; j < dim; j++) 
           {
              gbest[j] = dpbest[m_iTempPos][j];
           }
       }
    }
    public static void main(String[] args) throws IOException {
       psotest pso = new psotest();
       pso.c();
       File file = new File("E:\\JavaProgram\\psotest\\input\\file.txt");
       BufferedWriter out=new BufferedWriter(new FileWriter(file,true));
       for(int i = 0; i < sizepop; i++)
       {
          out.write(i + " ");
           for(int j= 0; j < dim; j++) 
           {
              out.write(pso.pop[i][j] + " "+ pso.V[i][j] + " " + pso.dpbest[i][j] + " " + pso.gbest[j] + " ");
           }
          out.write(pso.bestfitness[i]+" "+pso.g);
          out.newLine();
          //�ļ��е�ÿһ����һ�����ӣ����¼�����ݷֱ������ӵ�λ�ã����ӵ��ٶȣ����ӱ�������Ž��λ�ã�ȫ�����Ž��λ���Լ�ֵ
          //�ܹ�20�У����ӵ�λ��*2 + ���ӵ��ٶ�*2 + ���ӱ�������Ž��λ��*2 + ���Ӽ�¼ȫ�����Ž��λ��*2 + ȫ�����Ž� + ȫ�����Ž�λ�� �ܹ�10����
          //���ǵĸ�ʽ�ǣ�����λ��1+�����ٶ�1+���ӱ�������Ž��λ��1+���Ӽ�¼ȫ�����Ž��λ��1+
          				//����λ��2+�����ٶ�2+���ӱ�������Ž��λ��2+���Ӽ�¼ȫ�����Ž��λ��2+
          				//ȫ�����Ž� +ȫ�����Ž�λ��
       }
       out.close();
    }
}
