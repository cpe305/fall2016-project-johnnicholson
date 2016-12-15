# 3D Printer Rest Spec

All endpoints can only be accessed by authenticated users
 (referred to as AU from here out) with the exception of a post to /prss which
 is open for all users.

All endpoints are also prefixed with /api


# Person Resources

## /prss
#### POST
Used to create a new person.
* firstName
* lastName
* email - must be a calpoly.edu email unless AU is staff
* role - (optional) AU must be admin to specify roll other than student

#### GET
(optional queryPrm, limit) returns an array of prss with the optional limit, AU must be staff or above

Fields are the same as /prss/{prsId}

## /prss/{prsId}

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

## /prss/{prsId}/notes
#### GET
Get all notes associated with that person (either creator or recipient)

## /prss/{prsId}/reqs
#### POST
create a new request belonging to a person same fields as POST /reqs without
prsId, owner is prsId

#### GET
Get all reqs created by a person, returns the same fields as a get on /reqs/{reqId}
# Location Resources

## /locs
#### GET
Get a list of all locations fields same as POST

#### POST
create a new location AU must be admin
* name
* description

## /locs/{locId}
#### PUT
edit location information AU must be admin, same fields as POST

#### DELETE
remove location and all print requests AU must be admin

## /locs/{locId}/reqs
#### GET ? old:boolean
Get a list of all standing requests at a location (all requests if old is true), this is only allowed to staff and admin

# Request Resources

## /reqs/{reqId}
#### POST
* fileName
* createdAt
* location
* file

#### GET
Get the information about a specific requests, only allowed to staff, admin or owner
* id
* sequence - the order in line that this request is at
* fileName
* ownerId
* createdAt


#### PUT
Edit the metadata of a request
* sequence - the order in line that this request is at, AU must be Staff or above
* ownerId
* isFileEditable

#### DELETE
Remove the request, must be staff admin or owner

## /reqs/{reqId}/file
#### GET
Get the file attached to a request
#### PUT
Edit the file attached to this request

