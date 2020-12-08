package sample;

import java.security.Key;

public class Constants {
    public static final Key key = new Key() {
        @Override
        public String getAlgorithm() {
            return "AES";
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[] {-100, 22, 66, 7, 3, 118, -44, 83, 66, 45, -101, -46, -53, -23, 15, 68, -107, 101, 110, 87, 102, 51, 22, -31, 104, -58, -80, 38, -110, -73, -86, -27};
        }
    };
}
