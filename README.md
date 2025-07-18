# Influencer-OOP-Project


A Java-based object-oriented application simulating a professional platform for advertisers, brands, and influencers to collaborate efficiently on social media campaigns.



Authors
- Armaan  
- Cheryl  
- Subodh  
- V E Pavithraja  


Project Overview

This system models the real-world workflow of influencer marketing, including campaign creation, contract management, secure payments, and role-based dashboards. Built as part of an academic OOP group project, it demonstrates solid principles of object-oriented design and software architecture.


Core Features

 User Roles
Admin: System-level operations and analytics.
Advertiser: Creates campaigns and assigns ads.
Brand Manager: Manages contracts and processes payments.
Influencer: Views payments, tracks stats, joins campaigns.

 Campaign & Contract Flow
- Brands create campaigns via Brand Managers.
- Influencers are discovered and contracts are generated.
- Contract validation ensures data integrity across `brands.txt`, `campaigns.txt`, and `influencers.txt`.

Payment System
- Multi-method `processPayment()` overloads for flexible payments.
- Currency conversion support.
- Secure logging and receipt generation.
- Custom exception handling (`PaymentProcessingException`, `InvalidPaymentException`, `LoginException`).
 Authentication & Dashboards
- Abstract `User` class with polymorphic dashboards per role.
- Session control and login/logout with exception safety.


  Classes used

 `User` (abstract), `Admin`, `Advertiser`, `BrandManager`, `Influencer`
 `Contract`, `PaymentProcessor`, `PaymentHandler`
 `Dashboard` Interface
 `Main.java` for system control and user flow

---

 How to Run
1. Compile all `.java` files in the `src` directory.
2. Run `Main.java` to launch the application.
3. Login/Register with one of the supported user types.
4. Use the dashboard to perform campaign or payment actions based on your role.




 Object-oriented design using inheritance, encapsulation, and abstraction.
 Exception handling and validation logic.
File handling with `.txt` storage emulating backend databases.


