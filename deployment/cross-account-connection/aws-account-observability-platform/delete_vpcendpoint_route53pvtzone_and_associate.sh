#!/bin/bash

#Load Environment variables generated during create time through delete.env file
source delete.env


#Delete DNS Entry for VPC Endpoint from Route53 Private Hosted zone in Observability account

if [ -f dnsentry.json ]; then sed -i "s/CREATE/DELETE/g" dnsentry.json ; fi
aws route53 change-resource-record-sets \
    --hosted-zone $HOSTED_ZONE \
    --change-batch file://dnsentry.json \
    --profile ObsAccount


# Wait for deletion
sleep 60

#Delete Private hosted Zone from Observability Account
aws route53 delete-hosted-zone --id $HOSTED_ZONE --profile ObsAccount


#Finally Delete VPCEnd Point from Observability Account
aws ec2 delete-vpc-endpoints --vpc-endpoint-ids ${VPC_ENDPOINT_ID} --profile ObsAccount
