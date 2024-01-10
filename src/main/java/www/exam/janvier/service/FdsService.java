package www.exam.janvier.service;

import java.io.IOException;

public interface FdsService {
    public byte[] convertPdf(String filePath) throws IOException;
}
