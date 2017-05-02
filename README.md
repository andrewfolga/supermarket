The design uses the ports and adapters approach.

At the heart of the design is the Cashier. She processes the given basket and produces the final receipt.

For bonus REST resources and uris
=================================
Rest resources:
- POST /baskets/ (body containing empty basket)

Create basket 1. Not idempotent (must be applied atomically - cannot return partially updated item when GET is called).

- GET /baskets/

Get all current baskets. Idempotent.

- GET /baskets/1

Get basket number 1. Idempotent.

- DELETE /baskets/1

Removes basket 1 from system. Idempotent.

- POST /baskets/1/items/2 (body containing item for basket)

Add item 2 to basket 1. Not idempotent (must be applied atomically - cannot return partially updated item when GET is called).

- PUT /baskets/1/items/2 (body containing only the partial details of an item to be changed)

Overwrites/adds new item 2 in basket 1. Idempotent.

- PATCH /baskets/1/items/2 (body containing only the partial details of an item to be changed)

Partial update of item 2 details in basket 1. Not idempotent (must be applied atomically - cannot return partially updated item when GET is called).

- DELETE /baskets/1/items/2

Removes item 2 from basket 1. Idempotent.
