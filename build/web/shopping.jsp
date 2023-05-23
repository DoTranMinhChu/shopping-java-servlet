
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="main.dto.Product"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="styles/tailwindcss/tailwind-all.min.css">
        <title>Shopping</title>
        <style>
            /* Custom styles for the product list */
            .product-list {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 1rem;
                padding: 1rem;
            }

            .product-card {
                padding: 1rem;
                border-radius: 4px;
                width: 250px; /* Set a fixed width */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .product-card img {
                height: 300px; /* Set a fixed height */

                max-width: 100%;
                height: auto;
                margin-bottom: 1rem;
            }

            .product-card h2 {
                font-size: 1.2rem;
                font-weight: bold;
                margin-bottom: 0.5rem;
            }

            .product-card p {
                font-size: 0.9rem;
                color: #555;
            }        </style>
    </head>
    <body>


        <div class="container mx-auto">

            <div class="product-list">
                <%
                          ArrayList<Product> listProducts = (ArrayList<Product>) request.getAttribute("listProducts");
                          for (int i = 0; i < listProducts.size(); i++) {
                          Product item=  listProducts.get(i);
                          String productThumbnailUrl =  item.getThumbnailUrl();
                          String productName =  item.getName();
                          float productPrice =  item.getPrice();
                %>
                <div class="product-card">
                    <img class="product-image" src="<%=productThumbnailUrl%>" alt="<%=productName%>">
                    <h2 class="text-lg font-bold"><%=productName%></h2>
                    <p><%=productPrice%></p>
                </div>
                <%
                }
                %>
            </div>
        </div>
    </body>
</html>



</body>
</html>
