nuxeo-compoundform-sample
=========================

# About

This is a sample code to have a form where a complexlist is automatically populated depending on the value of an other field.

The goal is to have a template widget that :

 - contains the 'key' field
 - contains an ajaxRendered subLayout
 - use a valueChangedListener to pre-fill the subLayout depending on the value selected for the 'key' field

# Status

This is a work in progress.

For now, to make debugging simpler, the project includes an extract of a studio project that define the doctype, schemas and layouts.

The goal is to define the template widget and then to reintegrate it inside Nuxeo Studio : removing most of the config from this bundle.

Ideally, this bundle should only hold the Seam Beans.

# Open Questions

## valueChangeListener

To make the system work, I need to have a `valueChangeListener`.

I use `valueChangeListener` to prepopulate the n:ested layout depending on the current value.

This processing is done inside a Seam bean.

### Select2 and valueHolder

This works for a inputText, but when I try to use a select2, I need to bind a `valueChangeListener` to the `valueHolder`, but it does not seem to work.

### resolveTwice

Ideally, I would like the `valueChangeListener` to be configurable as widget property.
But in this case, I need to be able to do a double resolution.

I tried with :

    <nxu:set var="listener" value="#{widgetProperty_valueChangeListener}" resolveTwice="true">

But since the `valueChangeListener` is a method binding the double resolution fails.

## UIEditableList

During the processing of the valueChangeListener, I update the content of the UIEditableList.

Adding entries is not a problem.

However, I am not able to remove values or to reset it.





