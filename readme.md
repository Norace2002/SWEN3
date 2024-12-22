# Paperless WebService

## Introduction
This project is a clone of the popular document-management software "Paperless".

## Setup
To run the paperless service on your local machine:
- install and start docker
- navigate to the root folder of this project
- run **docker compose up --build**

## Components
### Web Server
The web-interface is built with react running on a nginx webserver. It offers the user the options to:
- store pdf documents
- view stored documents
- delete stored documents
- download stored documents as pdf files again

### REST Server
The REST server is the core of the application. It is a Java Spring server and handles communication between the other major components. 

**Available paths** can be viewed under http://localhost:8080/openapi

**ProvIDed Functionalities**:
- create new entries in document-object database
- delete entries in document-object database
- request objects from document-object database
- store bytestream in minIO with document-object ID
- send document-object ID to OCR service
- receive IDs from OCR service after successful OCR extraction
- request fulltext search from ElasticSearch

### OCR Server
The OCR server is also a Java Spring server and handles the OCR part of the application, which allows full text and fuzzy search by ElasticSearch later on.

**ProvIDed Functionalities**:
- receive bytestream and document-object ID from REST
- return document-object ID to REST after successful OCR extraction or NULL if failed
- request bytestream from minIO by document-object ID
- send full text content from OCR extraction with corresponding document-object ID to ElasticSearch

### Database
The database is written in PostgreSQL and manages the stored document-objects, which contain mainly the ID as well as corresponding information for each stored document.

### MinIO
MinIO takes care of storing the pdf bytestreams with a corresponding ID for further usage.

### RabbitMQ
RabbitMQ is the communication pipeline between REST and OCR. It makes sure that the OCR service can work independently from the REST service and ensures asynchronous functionality.

### ElasticSearch
ElasticSearch acts as a storage for the full-text content of the stored documents. Each stored text is equipped with a corresponding ID which matches the document-object ID in the database as well as the ID corresponding to the bytestream in MinIO.

### Kibana
Kibana acts as a GUI for ElasticSearch