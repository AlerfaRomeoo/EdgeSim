class test {
	public static void main(String[] args) {
		// ����Gbu û�е�λ
		double dbu = 100;// ��λ����
		double Gbu = 140.7 + 36.7 * Math.log10(dbu * 0.001); // ��ʧ+
		System.out.println("��ʧ:" + Gbu);
		
		double Pb = 46.020599913279625; // ��λ��W
		

		// ���������
		double temp = -174; // ��λdBm/HZ
		double o = (Math.pow(10, temp / 10)) * 0.001; // ��λW/HZ
		double SNR = Pb*Gbu / temp;
		System.out.println(SNR);
		
//		SNR = 30;
//		double sn = Math.pow(10, SNR/10);
//		System.out.println(sn);

		// ���������ٶ�
		double B = 10000000; // ��λHZ
		double w = B * (Math.log(1 + SNR) / Math.log(2));

		System.out.println(w / 8 / 1024 / 1024 + " MB/s");
		
		System.out.println("______________");
//		new test().add();
	}

	public void add() {
		// ����Gbu û�е�λ
		double dbu = 350;// ��λ����
		double k = 0.01;
		double e = 4;
		double Gbu = k*Math.pow(dbu, -e);
		
		double Pb = 40; // ��λ��W
		
		double temp = -174; // ��λdBm/HZ

		double o = (Math.pow(10, temp/10)) * 0.001; // ��λW/HZ

		double SNR = Pb*Gbu/(o*10000000);
		
//		System.out.println(SNR);
//
//		double sn = Math.pow(10, SNR/10);
//		System.out.println(sn);
		
//		sn = 30;
////		
//		SNR = Math.log10(30)*10;
//		System.out.println(SNR);
//		System.out.println(14.77*);
		
		double B = 10000000; // ��λHZ
		double w = B * (Math.log(1 + SNR) / Math.log(2));

		System.out.println(w / 8 / 1024 / 1024 + " MB/s");
	}
}