package gkfire.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("EnumConverter")
public class EnumConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        if ((value == null) || (value.length() < 1)) {
            return null;
        }

        int pos = value.indexOf('@');
        if (pos < 0) {
            throw new IllegalArgumentException(value + " do not point to an enum");
        }

        String className = value.substring(0, pos);

        int ordinal = Integer.parseInt(value.substring(pos + 1), 10);

        Class clazz = null;
        try {
            clazz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());

            while ((clazz != null) && (!clazz.isEnum())) {
                clazz = clazz.getSuperclass();
            }
            if (clazz == null) {
                throw new IllegalArgumentException("class " + className + " couldn't be treated as enum");
            }

            Enum[] enums = (Enum[]) clazz.getEnumConstants();
            if (enums.length >= ordinal) {
                return enums[ordinal];
            }
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }
        throw new IllegalArgumentException("ordinal " + ordinal + " not found in enum " + clazz);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException {
        if (value == null) {
            return "";
        }

        Enum<?> e = (Enum) value;

        if (((component instanceof UIInput)) || ("javax.faces.Input".equals(component.getFamily()))) {
            return e.getClass().getName() + "@" + Integer.toString(e.ordinal(), 10);
        }

        return e.name();
    }
}
