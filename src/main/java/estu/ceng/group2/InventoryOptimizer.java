package estu.ceng.group2;
import java.util.Scanner;

/**
 * Main application for determining the optimal lot size (Q) and reorder point (R).
 * Developed for ENM320 and BİM2006 projects.
 */
public class InventoryOptimizer {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            /**
             * 1. INPUT PHASE
             * Requesting cost and demand parameters from the user.
             */
            System.out.print("Enter unit cost ($): ");
            double unitCost = scanner.nextDouble();
            
            System.out.print("Enter ordering cost (K): ");
            double orderingCost = scanner.nextDouble();
            
            System.out.print("Enter penalty cost (p): ");
            double penaltyCost = scanner.nextDouble();
            
            System.out.print("Enter annual interest rate (e.g., 0.25): ");
            double interestRate = scanner.nextDouble();
            
            System.out.print("Enter lead time (months): ");
            double leadTime = scanner.nextDouble();
            
            System.out.print("Enter average demand during lead time (mu): ");
            double meanDemand = scanner.nextDouble();
            
            System.out.print("Enter standard deviation of lead time demand (sigma): ");
            double stdDev = scanner.nextDouble();
            
            /**
             * 2. PRE-CALCULATIONS
             * Calculating annual demand and holding cost.
             */
            double annualDemand = meanDemand * (12.0 / leadTime);
            double holdingCost = interestRate * unitCost;
            
            /**
             * 3. INITIALIZATION
             * Setting up initial values for the iteration loop.
             */
            double expectedShortage = 0.0;
            double previousZ = -1.0;
            double currentZ = 0.0;
            
            int iterations = 0;
            int maxIterations = 1000; // Failsafe limit to prevent infinite loops
            
            double lotSize = 0.0;
            double serviceLevel = 0.0;
            
            /**
             * 4. ALGORITHM LOOP
             * Iterating until Z-scores stabilize or max iterations are reached.
             */
            while (Math.abs(previousZ - currentZ) > 0.0001 || iterations == 0) {
                
                // Failsafe mechanism
                if (iterations >= maxIterations) {
                    System.out.println("\nWARNING: Maximum iteration limit (" + maxIterations + ") reached.");
                    System.out.println("The results might not be fully converged.");
                    break;
                }
                
                previousZ = currentZ;
                iterations++;
                
                // Calculate Order Quantity (Q)
                lotSize = Math.sqrt((2.0 * annualDemand * (orderingCost + (penaltyCost * expectedShortage))) / holdingCost);
                
                // Calculate Service Level Probabilities
                double stockoutProb = (holdingCost * lotSize) / (penaltyCost * annualDemand);
                serviceLevel = 1.0 - stockoutProb;
                
                // Lookup statistical values from the separate utility class
                currentZ = InventoryStatistics.lookupZTable(serviceLevel);
                double lossValue = InventoryStatistics.lookupLossFunction(currentZ);
                
                // Update n(R)
                expectedShortage = stdDev * lossValue;
            }
            
            /**
             * 5. FINAL CALCULATIONS
             * Determining final decision variables and performance measures.
             */
            long finalLotSize = Math.round(lotSize);
            long reorderPoint = Math.round(meanDemand + (currentZ * stdDev));
            long safetyStock = reorderPoint - Math.round(meanDemand);
            
            double setupCost = orderingCost * (annualDemand / finalLotSize);
            double annualHoldingCost = holdingCost * ((finalLotSize / 2.0) + safetyStock);
            double annualPenaltyCost = penaltyCost * (annualDemand / finalLotSize) * expectedShortage;
            
            double timeBetweenOrders = (finalLotSize / annualDemand) * 12.0;
            double unmetDemandProportion = expectedShortage / finalLotSize;
            
            /**
             * 6. OUTPUT RESULTS
             * Displaying all calculated performance measures.
             */
            System.out.println("\n--- FINAL OPTIMIZATION RESULTS ---");
            System.out.println("Optimal Lot Size (Q): " + finalLotSize);
            System.out.println("Reorder Point (R): " + reorderPoint);
            System.out.println("Iterations Performed: " + iterations);
            System.out.println("Safety Stock: " + safetyStock);
            System.out.printf("Annual Holding Cost: $%.2f\n", annualHoldingCost);
            System.out.printf("Annual Setup Cost: $%.2f\n", setupCost);
            System.out.printf("Annual Penalty Cost: $%.2f\n", annualPenaltyCost);
            System.out.printf("Time Between Orders (Months): %.3f\n", timeBetweenOrders);
            System.out.printf("Probability of No Stockout: %.4f\n", serviceLevel);
            System.out.printf("Proportion of Demands Not Met: %.4f\n", unmetDemandProportion);
        }
    }
}