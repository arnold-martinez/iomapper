package io.dojogeek.sayamapper;

import java.util.logging.Logger;

/**
 * TargetWrapper is a wrapper to handle easily the target class filling.
 *
 * @author norvek
 */
public class TargetWrapper<T> extends MergeableObject {

    private final static Logger LOGGER = Logger.getLogger(TargetWrapper.class.getName());

    private Class<T> target;
    private Object targetInstance;
    private CustomMappings customRelations;
    private IgnorableFields ignorableFields;

    /**
     * TargetWrapper constructor.
     *
     * @param targetClass  the target class.
     */
    public TargetWrapper(Class<T> targetClass) {
        this.target = targetClass;
    }

    /**
     * Returns a target filled instance from source object.
     *
     * @param source  the source object wrapper.
     * @return        a TargetWrapper object.
     */
    public TargetWrapper<T> populateWith(SourceObject source) {

        try {
            this.targetInstance = this.target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("An error occurred when instantiating: " + this.target + "\n" + e.getMessage());
        }

        super.merge(source, this.targetInstance, this.ignorableFields, this.customRelations);

        return this;
    }

    /**
     * Sets a list of fields to fill.
     *
     * @param ignorableFields  a list of fields to fill.
     */
    public TargetWrapper<T> ignore(IgnorableFields ignorableFields) {
        if (ignorableFields != null) {
            this.ignorableFields = ignorableFields;
        }

        return this;
    }

    /**
     * Sets a fill of the custom relations mapping.
     *
     * @param customizable  a fill with the custom relations.
     */
    public TargetWrapper<T> relate(CustomMappings customizable) {
        if (customizable != null) {
            this.customRelations = customizable;
        }

        return this;
    }

    public T get() {
        return (T) this.targetInstance;
    }

}
