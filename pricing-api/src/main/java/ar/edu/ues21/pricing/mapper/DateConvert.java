package ar.edu.ues21.pricing.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class DateConvert extends AbstractBeanField {
   private static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/M/yyyy");

   @Override
   public LocalDate convert(String dateString) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
      try {
         return LocalDate.parse(dateString, formatter1);
      } catch (DateTimeParseException e1) {
         try {
            return LocalDate.parse(dateString, formatter2);
         } catch (DateTimeParseException e2) {
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy or yyyy-MM-dd.");
         }
      }
   }
}
