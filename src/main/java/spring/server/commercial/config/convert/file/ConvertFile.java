package spring.server.commercial.config.convert.file;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

public class ConvertFile {
	public static Blob convertMultipartFileToBlob(MultipartFile multipartFile)
			throws SerialException, SQLException, IOException {

		return new SerialBlob(multipartFile.getBytes());

	}

	public static Blob bytesToBlob(byte[] bytes) throws SerialException, SQLException {
		return new SerialBlob(bytes);
	}

	public static byte[] extractBytesFromBlob(Blob blob) {
		if (blob == null)
			return null;
		try (InputStream inputStream = blob.getBinaryStream()) {
			int blobLength = (int) blob.length();
			byte[] bytes = new byte[blobLength];
			inputStream.read(bytes, 0, blobLength);
			return bytes;
		} catch (SQLException | IOException e) {
			return null;
		}
	}
}
