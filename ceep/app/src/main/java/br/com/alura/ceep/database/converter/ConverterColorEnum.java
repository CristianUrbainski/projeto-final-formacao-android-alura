package br.com.alura.ceep.database.converter;

import org.apache.commons.lang3.StringUtils;

import br.com.alura.ceep.ui.enumerator.ColorEnum;

import androidx.room.TypeConverter;

/**
 * @author Cristian Urbainski
 * @since 1.0 (04/06/21)
 */
public class ConverterColorEnum {

    @TypeConverter
    public static String toString(ColorEnum colorEnum) {

        if (colorEnum == null) {

            return null;
        }

        return colorEnum.name();
    }

    @TypeConverter
    public static ColorEnum toColorEnum(String value) {

        if (StringUtils.isEmpty(value)) {

            return null;
        }

        return ColorEnum.valueOf(value);
    }

}
