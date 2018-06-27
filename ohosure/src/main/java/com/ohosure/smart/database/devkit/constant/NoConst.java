package com.ohosure.smart.database.devkit.constant;

/**
 * Number constant class.
 * 
 * @author daxingj
 */
public interface NoConst
{
	/**
	 * Buffer size is defined as 4096 usually.
	 */
	int BUF_HIGH_SIZE = 4096;

	/**
	 * Buffer size is defined as 1024 usually.
	 */
	int BUF_NORMAL_SIZE = 1024;

	/**
	 * Buffer size is defined as 256 usually.
	 */
	int BUF_LOW_SIZE = 256;

	/**
	 * Length of basic location.
	 */
	int BASIC_LOCATION_LEN = 28;

	/**
	 * Offset from head flag. It equals to the difference between head flag and
	 * length flag.
	 */
	int LEN_OFFSET = 2;

	/**
	 * Length of all exception length of data area. It contains headFlag, type,
	 * length, crc1, crc2 and endFlag. They are totally 6 bytes.
	 */
	int LEN_EXCEPT_DATA = 6;

	int PORT = 6000;

	/**
	 * Max unsigned byte value.
	 */
	int MAX_UNSIGNED_BYTE = 256;

	

	/**
	 * Minimum byte quantity in protocol serial interface. <br/>
	 */
	int MIN_SERIAL_DATA_LENGTH = 7;

	/**
	 * Max value of a byte. This usually acts as a buffer size.
	 */
	int A_BYTE = 256;
	/**
	 * Int 8 bits
	 */
	int I8 = 0x000000FF;
	/**
	 * Int 16 bits
	 */
	int I16 = 0x0000FFFF;
	/**
	 * Int 24 bits
	 */
	int I24 = 0x00FFFFFF;
	/**
	 * Long 32 bits
	 */
	long L32 = 0xFFFFFFFFL;
	/**
	 * maximum time to wait for combining a long message in milliseconds
	 */
	long MAX_WAIT_MS = 100L;
	/**
	 * 6 bytes BCD code which indicates 12 digit bit phone number.
	 */
	int PHONE_NUMBER_BYTES = 6;
	/**
	 * Escape symbol for 0xFB.
	 */
	byte ESCAPE_0XFB = (byte) 0xFB;
	/**
	 * Escape symbol for 0xFE.
	 */
	byte ESCAPE_0XFE = (byte) 0xFE;
	/**
	 * Escape symbol for 0xFD.
	 */
	byte ESCAPE_0XFD = (byte) 0xFD;
	/**
	 * Escape symbol for 0x01.
	 */
	byte ESCAPE_0X01 = 0x01;
	/**
	 * Escape symbol for 0x02.
	 */
	byte ESCAPE_0X02 = 0x02;
	/**
	 * Escape symbol for 0x03.
	 */
	byte ESCAPE_0X03 = 0x03;
	/**
	 * Escape symbol for 0x7e.
	 */
	byte ESCAPE_0X7E = 0x7e;
	/**
	 * Escape symbol for 0x7d.
	 */
	byte ESCAPE_0X7D = 0x7d;

	/**
	 * Initial socket connecting timeout. Unit is milliseconds.
	 */
	int SOCKET_MIN_TIMEOUT = 32;

	/**
	 * Maximum of socket connecting timeout. Unit is milliseconds.
	 */
	int SOCKET_MAX_TIMEOUT = 1024;

	/**
	 * The minimum of serial interface data length. If it is less than this
	 * length, it must be illegal. It contains TYPE(1), LENGTH(2), CENTER_ID(1),
	 * 808HEADER(10).
	 */
	// int MIN_SERIAL_DIRECT_DATA_LENGTH = 14;

	/**
	 * The 7th bit position is 1 only.
	 */
	int BIT_7 = 1 << 7;

	/**
	 * Long info cache time (second).
	 */
	long CACHE_CHECK_RATE = 100;

	/**
	 * Long info cache time (millisecond).
	 */
	long CACHE_CLEAN_TIME_UNIT = 500;

	/**
	 * Poll interval from platform bytes (millisecond).
	 */
	long PLATFORM_INFO_INTERVAL = 200;

	/**
	 * Max size of multi media packet in a frame.
	 */
	int MM_PACKET_MAX_SIZE = 1000;

	/**
	 * Camera save video interval(second)
	 */
	int RECORDING_TIME_INTERVAL = 60 * 5;

	/**
	 * Camera save video interval(second) for debugging quickly.
	 */
	int RECORDING_TIME_INTERVAL_DEBUG = 5;

	/**
	 * Camera save photo min interval(millisecond).
	 */
	int PHOTO_TIME_MIN_INTERVAL = 5000;

	/**
	 * Camera save photo min interval(millisecond).
	 */
	int MIN_HEARTBEAT_INTERVAL = 2000;

	/**
	 * Max byte array size. If your array size is bigger than this, maybe you
	 * will get a OutOfMemoryException instance.
	 */
	int MAX_BYTE_ARRAY_SIZE = 2560000;

	/**
	 * Media Type.
	 */
	int MEDIA_TYPE_VIDEO = 2;
	int MEDIA_TYPE_SOUND = 1;
	int MEDIA_TYPE_PHOTO = 0;
	
	/**
	 * Fixed frame size to upgrade MCU
	 */
	int UPGRADE_FRAME_SIZE = 128;
	
	/**
	 * How many frames CPU send at a time before MCU request.
	 * CPU send FRAMES_ONCE frames once, then MCU send request once. 
	 */
	int FRAMES_ONCE = 8;
}
