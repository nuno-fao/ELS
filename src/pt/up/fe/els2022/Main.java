package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		List<String> headers = new ArrayList<>();
		List<String> elements = new ArrayList<>();

		headers.add("LUTs");
		headers.add("FFs");
		headers.add("DSPs");
		headers.add("BRAMSs");

		elements.add("LUT");
		elements.add("FF");
		elements.add("DSP48E");
		elements.add("BRAM_18K");

		HashMap<String, String> entry = XMLAdapter.parseFile("vitis-report_1.xml", headers, elements, "Resources");

		for (Map.Entry<String, String> item : entry.entrySet()) {
			System.out.println("Key: " + item.getKey() + " Value: " + item.getValue());
		}
	}
}