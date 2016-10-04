# 3D Printer Rest Spec

All endpoints can only be accessed by authenticated users
 (referred to as AU from here out) with the exception of a post to /Prss which
 is open for all users.

All endpoints are also prefixed with /api

## Person Resources

### /prss

#### POST
Used to create a new person.
* firstName
* lastName
* email - must be a calpoly.edu email unless AU is staff
* role - (optional) AU must be staff to specify roll other than student

#### GET
(optional queryPrm, limit) returns an array of prss with the optional limit, AU must be staff or above

Fields are the same as /prss/{prsId}

### /prss/{prsId}

#### GET
Must be admin or prsId must be AU
* firstName
* lastName
* role
* email

#### PUT
Same fields as for GET, except email cannot be modified

#### DELETE
Removes the person in question, AU must be the person or staff
