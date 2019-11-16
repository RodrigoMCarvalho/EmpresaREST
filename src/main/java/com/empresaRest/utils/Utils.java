package com.empresaRest.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Utils {
	
	public static BigDecimal calcularPorcentual(BigDecimal total, BigDecimal valor) {
		return valor.divide(total, 2, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100));
	}
	
	public static String formatarCPF(String cpf) throws ParseException {
		MaskFormatter mask = new MaskFormatter("###.###.###-##");
		mask.setValueContainsLiteralCharacters(false);
		return mask.toString();
	}
}
