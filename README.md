nuxeo-compoundform-sample
=========================

# About

This is a sample code to have a form where a complexlist is automatically populated depending on the value of an other field.

The goal is to have a template widget that :

 - contains the 'key' field
 - contains an ajaxRendered subLayout
 - use a Seam bean to pre-fill the subLayout depending on the value selected for the 'key' field

# Status

All the configuration comes from a Studio project called `compound-form`.

To avoid having to add Studio credentials to the build, a compy of the studio jar in available in the studio directory :

    studio/compound-form.jar

# Deploying

## Nuxeo IDE

This java module can be deployed via Nuxeo IDE.

In this case, you should reference the Studio `compound-form` project so that everything is deployed at the same time.

## Bare Java

Build the jar : 

    mvn clean install

Deploy the jar

    cp target/nuxeo-compoundform-sample-5.9.3.jar $NXSERVER/plugins/.

Deploy the Studio jar 

    cp studio/compound-form.jar $NXSERVER/plugins/.

And restart the Nuxeo Server

# Configuring

There are 2 places to adjust the way this sample works.

## Inside Nuxeo Studio

Everything  is driven by the `generic` widget inside the Master layout in `create` mode.

You can use the widget properties to define :

 - the name of the subLayout
 - the Seam Beans bindings

## Inside the Nuxeo IDE project

If you adjust the schemas, you will need to make sure the `getComplexValues` of the `ComplexSelectorBean` returns the correct values.

The idea is that you may of course have several bindings : you will then need several Beans.

NB : Because of some hot-reload issues in Nuxeo IDE with Seam Beans, the `ComplexSelectorBean` has no base class, but in the real world it should.

