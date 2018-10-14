package com.nompor.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamFactory {
	InputStream newInputStream() throws IOException;
}
