package io.dojogeek.sayamapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InspectableObject {

    private Object object;

    public InspectableObject(Object object) {
        this.object = object;
    }

    public List<FlexibleField> getDeclaredFields() {
        return this.getFlexibleFieldList();
    }

    public List<FlexibleField> getDeclaredFieldsIgnoring(IgnorableList ignorableList) {
        List<FlexibleField> flexibleFields = this.getFlexibleFieldList();

        if (ignorableList == null) {
            return flexibleFields;
        }

        flexibleFields.forEach(field -> {
            if (ignorableList.hasFieldNamed(field.getName())) {
                field.ignore();
                return;
            }

            IgnorableList ignorableFields = ignorableList.getIgnorableFor(field.getName());

            if (!ignorableFields.isEmpty()) {
                field.setIgnorable(ignorableFields);
            }
        });

        return flexibleFields;
    }

        public List<FlexibleField> getFlexibleFieldList() {
            List<FlexibleField> fields = new ArrayList<>();

            Stream.of(this.object.getClass().getDeclaredFields()).forEach(field -> {
                if (field.getType().isPrimitive() ||
                        field.getType().isAssignableFrom(Byte.class) ||
                        field.getType().isAssignableFrom(Short.class) ||
                        field.getType().isAssignableFrom(Integer.class) ||
                        field.getType().isAssignableFrom(Long.class) ||
                        field.getType().isAssignableFrom(Float.class) ||
                        field.getType().isAssignableFrom(Double.class) ||
                        field.getType().isAssignableFrom(String.class)) {
                    fields.add(new JavaField(field, this.object));
                    return;
                }

                fields.add(new ForeignField(field, this.object));
            });

            return fields;
        }

}
