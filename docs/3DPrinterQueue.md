# 3D Printer Rest Spec

All endpoints can only be accessed by authenticated users
 (referred to as AU from here out) with the exception of a post to /prss which
 is open for all users.

All endpoints are also prefixed with /api

# Person Resources

# /prss

#### POST
Used to create a new person.
* firstName
* lastName
* email - must be a calpoly.edu email unless AU is staff
* role - (optional) AU must be staff to specify roll other than student

#### GET
(optional queryPrm, limit) returns an array of prss with the optional limit, AU must be staff or above

Fields are the same as /prss/{prsId}

# /prss/{prsId}

#### GET
Must be admin or prsId must be AU
* firstName
* lastName
* role
* email

#### PUT
Same fields as for GET, except email cannot be modified and modifying role is only
allowed by admin.

#### DELETE
Removes the person in question, AU must be the person or staff

# /prss/{prsId}/reqs
#### GET
Get all reqs created by a person, returns the same fields as a get on /reqs/{reqId}
# /prss/{prsId}/notes
#### GET
Get all notes associated with that person (either creator or recipient)

# Request Resources

# /reqs
#### GET ? old:boolean
Get a list of all standing requests (all requests if old is true), this is only allowed to staff and admin
#### POST
Create a new request
* file
* description
* ownerId (optional) only allowed if AU is admin or staff

# /reqs/{reqId}
#### GET
Get the information about a specific requests, only allowed to staff, admin or owner
* id
* sequence - the order in line that this request is at
* fileSize (maybe)
* fileName
* description
* ownerId
* isFileEditable
* dateCreated

#### PUT
Edit the metadata of a request
* sequence - the order in line that this request is at
* description
* ownerId
* isFileEditable

#### DELETE
Remove the request, must be staff admin or owner

### /reqs/{reqId}/file
Get the file attached to a request
#### PUT
Edit the file attached to this request only allowed to staff or admin, previously set to allow changes by admin/staff

# Notification Resources
# /notes
#### GET
#### POST
# /notes/{noteId}
#### GET
#### PUT
