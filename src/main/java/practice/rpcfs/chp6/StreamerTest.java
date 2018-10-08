package practice.rpcfs.chp6;

import java.io.*;

public class StreamerTest {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        FileOutputStream outputStream = new FileOutputStream("");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();

        bufferedReader.close();
        bufferedWriter.close();
        inputStream.close();
        outputStream.close();
    }
}
