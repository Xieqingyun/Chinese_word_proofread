package ICTCLAS.kevin.zhang;

//import java.util.*;
//import java.io.*;


class Result {
	public int start; //start position,��������������еĿ�ʼλ��
	public int length; //length,����ĳ���
	//public char sPOS[8]; //����
	public int posId;//word type������IDֵ�����Կ��ٵĻ�ȡ���Ա�
	public int wordId; //�����δ��¼�ʣ����0����-1
	public int word_type; //add by qp 2008.10.29 �����û��ʵ�;1�����û��ʵ��еĴʣ�0�����û��ʵ��еĴ�

  public int weight;//add by qp 2008.11.17 word weight
};



public class TestICTCLAS2011 {

	public static void main(String[] args) throws Exception
	{
		try
		{
			String sInput = "�������121213";

			//�ִ�
			Split(sInput);

			//��UTF8���зִʴ���
			//SplitUTF8();

			//��BIG5���зִʴ���
			//SplitBIG5();
		}
		catch (Exception ex)
		{
		} 


	}

	public static void Split(String sInput)
	{	
	try{	
		ICTCLAS2011 testICTCLAS2011 = new ICTCLAS2011();

		String argu = "F:\\workspace\\wordAutoErrorCorrection\\";
		System.out.println("ICTCLAS_Init");
		if (ICTCLAS2011.ICTCLAS_Init(argu.getBytes("GB2312"),0) == false)
		{
			System.out.println("Init Fail!");
			return;
		}

		/*
		 * ���ô��Ա�ע��
		        ID		    ������Լ� 
				1			������һ����ע��
				0			������������ע��
				2			���������ע��
				3			����һ����ע��
		*/
		testICTCLAS2011.ICTCLAS_SetPOSmap(2);

		//�����û��ʵ�ǰ
		byte nativeBytes[] = testICTCLAS2011.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 0);
		String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");

		System.out.println("δ�����û��ʵ䣺 " + nativeStr);
		
		//�ļ��ִ�
		String argu1 = "movie.txt";
		String argu2 = "TestGBK_result.txt";
		testICTCLAS2011.ICTCLAS_FileProcess(argu1.getBytes("GB2312"), argu2.getBytes("GB2312"), 0);
		
/*
		//�����û��ʵ�
		String sUserDict = "userdic.txt";
		int nCount = testICTCLAS2011.ICTCLAS_ImportUserDict(sUserDict.getBytes("GB2312"));
		testICTCLAS2011.ICTCLAS_SaveTheUsrDic();//�����û��ʵ�
		System.out.println("������û��ʣ� " + nCount);

		nativeBytes = testICTCLAS2011.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 1);
		nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");

		System.out.println("�����û��ʵ�� " + nativeStr);

		//��̬����û���
		String sWordUser = "973ר������֯������	ict";
		testICTCLAS2011.ICTCLAS_AddUserWord(sWordUser.getBytes("GB2312"));
		testICTCLAS2011.ICTCLAS_SaveTheUsrDic();//�����û��ʵ�			
		
		nativeBytes = testICTCLAS2011.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 1);
		nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
		System.out.println("��̬����û��ʺ�: " + nativeStr);

		//�ִʸ߼��ӿ�
		nativeBytes = testICTCLAS2011.nativeProcAPara(sInput.getBytes("GB2312"));

		int nativeElementSize = testICTCLAS2011.ICTCLAS_GetElemLength(0);//size of result_t in native code
		int nElement = nativeBytes.length / nativeElementSize;

		byte nativeBytesTmp[] = new byte[nativeBytes.length];

		//�ؼ�����ȡ
		int nCountKey = testICTCLAS2011.ICTCLAS_KeyWord(nativeBytesTmp, nElement);

		Result[] resultArr = new Result[nCountKey];
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(nativeBytesTmp));

		int iSkipNum;
		for (int i = 0; i < nCountKey; i++)
		{
			resultArr[i] = new Result();
			resultArr[i].start = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(1) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].length = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(2) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}

			dis.skipBytes(testICTCLAS2011.ICTCLAS_GetElemLength(3));

			resultArr[i].posId = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(4) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].wordId = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(5) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].word_type = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(6) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}
			resultArr[i].weight = Integer.reverseBytes(dis.readInt());
			iSkipNum = testICTCLAS2011.ICTCLAS_GetElemLength(7) - 4;
			if (iSkipNum > 0)
			{
				dis.skipBytes(iSkipNum);
			}				

		}

		dis.close();

		for (int i = 0; i < resultArr.length; i++)
		{
			System.out.println("start=" + resultArr[i].start + ",length=" + resultArr[i].length + "pos=" + resultArr[i].posId + "word=" + resultArr[i].wordId + "  weight=" + resultArr[i].weight);
		}*/

		
		//�ͷŷִ������Դ
		ICTCLAS2011.ICTCLAS_Exit();
	}
	catch (Exception ex)
	{
	} 

	}

	public static void SplitUTF8()
	{
		try
		{

		
		ICTCLAS2011 testICTCLAS2011 = new ICTCLAS2011();

		String argu = ".";
		if (ICTCLAS2011.ICTCLAS_Init(argu.getBytes("GB2312"),1) == false)
		{//UTF8�з�
			System.out.println("Init Fail!");
			return;
		}
		String argu1 = "TestUTF.txt";
		String argu2 = "TestUTF_result.txt";
		testICTCLAS2011.ICTCLAS_FileProcess(argu1.getBytes("GB2312"), argu2.getBytes("GB2312"), 1);


		//�ͷŷִ������Դ
		ICTCLAS2011.ICTCLAS_Exit();
		}
		catch (Exception ex)
		{
		} 
	}
	
	public static void SplitBIG5()
	{
		try
		{

		
		ICTCLAS2011 testICTCLAS2011 = new ICTCLAS2011();

		String argu = ".";
		if (ICTCLAS2011.ICTCLAS_Init(argu.getBytes("GB2312"),2) == false)
		{//UTF8�з�
			System.out.println("Init Fail!");
			return;
		}
		String argu1 = "TestBIG.txt";
		String argu2 = "TestBIG_result.txt";
		testICTCLAS2011.ICTCLAS_FileProcess(argu1.getBytes("GB2312"), argu2.getBytes("GB2312"), 1);


		//�ͷŷִ������Դ
		ICTCLAS2011.ICTCLAS_Exit();
		}
		catch (Exception ex)
		{
		} 
	}
}
 
