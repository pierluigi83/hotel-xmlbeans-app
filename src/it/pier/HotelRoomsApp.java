package it.pier;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.xmlbeans.XmlException;
import org.opentravel.ota.x2003.x05.OTAHotelAvailRSDocument;
import org.opentravel.ota.x2003.x05.OTAHotelAvailRSDocument.OTAHotelAvailRS;
import org.opentravel.ota.x2003.x05.OTAHotelAvailRSDocument.OTAHotelAvailRS.RoomStays;
import org.opentravel.ota.x2003.x05.OTAHotelAvailRSDocument.OTAHotelAvailRS.RoomStays.RoomStay;
import org.opentravel.ota.x2003.x05.RateType;
import org.opentravel.ota.x2003.x05.RoomRateType;
import org.opentravel.ota.x2003.x05.RoomStayType;
import org.opentravel.ota.x2003.x05.RoomStayType.RoomRates;
import org.opentravel.ota.x2003.x05.RoomTypeType;


public class HotelRoomsApp
{
	public static OTAHotelAvailRS xmlRead(String xmlPath) throws XmlException, IOException
	{
		File xmlFile = new File(xmlPath); 
		OTAHotelAvailRSDocument hoteldoc =    OTAHotelAvailRSDocument.Factory.parse(xmlFile);
		return hoteldoc.getOTAHotelAvailRS();
	}

	public static void main(String[] args) throws XmlException, IOException 
	{
		String xmlPath = "OTA_HotelAvailRS.xml";
		OTAHotelAvailRS hotel = xmlRead(xmlPath);
		RoomStays roomStays =  hotel.getRoomStays();
		RoomStayType.RoomTypes roomTypes = roomStays.getRoomStayArray(0).getRoomTypes();
		RoomStay roomStay = roomStays.getRoomStayArray(0);
		String hotelName = roomStay.getBasicPropertyInfo().getHotelName();
		System.out.println(" il nome dell\' hotel è : " + hotelName);
		RoomRates roomRates = roomStay.getRoomRates();
		for (RoomTypeType roomType : roomTypes.getRoomTypeArray())
		{
			String type = roomType.getRoomDescription().getTextArray(0).getStringValue();
			String InvBlockCode = roomType.getInvBlockCode();
			System.out.println(" camera " + type + ", codice inventario : " + InvBlockCode);
		}
		for (RoomRateType roomRateType : roomRates.getRoomRateArray())
		{
			RateType.Rate rateType = roomRateType.getRates().getRateArray(0);
			BigDecimal amount = rateType.getBase().getAmountAfterTax();
			String InvBlockCode = roomRateType.getInvBlockCode();
			System.out.println(" prezzo camera  con codice inventario " + InvBlockCode +  " : " + amount);
		}
	}

}
