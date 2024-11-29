# Auth System

## Overview

The Auth System is a modular system that provides robust authentication and role-based access control (RBAC). This service handles the creation and management of permissions, roles, and accounts, allowing for flexible access control across various resources.

## Key Features
- User Authentication: Supports various authentication strategies like JWT-based access.
- Role-Based Access Control (RBAC): Assign multiple roles and permissions to users for fine-grained access control.
- Permission Management: Create and manage permissions that define actions on resources.
- Role Management: Create roles and assign permissions to roles.
- Account Management: Assign roles to accounts and retrieve their permissions.

## Authorization management

### Supported Resources

The system supports access control for the following resources:

- structure: Represent structures.
- account: Represents user accounts within the system.
- permission: Represents permission assigned to a role.
- role: Represents a role assigned to users.

### Supported Actions

The following actions are supported across all resources:

- read: Allows reading/viewing of a resource.
- write: Allows creating or modifying a resource.
- delete: Allows deleting a resource.
- manage: Allows above all access on a resource.

Access can be granted for a specific resource or all resources of a given type.
For example:

- Specific Resource: hasPermission(101, 'role', 'read') — Grants read access to a specific role with ID 101.
- All Resources: hasPermission(-1, 'role', 'manage') — Grants manage access to all roles.